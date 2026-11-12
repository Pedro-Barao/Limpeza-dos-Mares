(() => {
    'use strict';

    const API_URL = '/api/denuncias';

    const ENDERECO_ID_CONFIGURADO = null;

    const form = document.querySelector('#form-denuncia');
    if (!form) return;

    const campoDescricao = document.querySelector('#descricao');
    const contador = document.querySelector('#contador');
    const campoTipo = document.querySelector('#tipo');
    const campoPerigo = form.querySelector('input[name="periculosidade"]:checked');
    const statusLocal = document.querySelector('#status-local');
    const botaoLocal = document.querySelector('#btn-local');
    const campoLatitude = document.querySelector('#latitude');
    const campoLongitude = document.querySelector('#longitude');
    const mensagemSucesso = document.querySelector('#sucesso');

    const TIPOS_RESIDUO = {
        DOMESTICO: 'LIQUIDO',
        INDUSTRIAL: 'LIQUIDO'
    };

    function atualizarContador() {
        if (contador && campoDescricao) {
            contador.textContent = `${campoDescricao.value.length}/100`;
        }
    }

    campoDescricao?.addEventListener('input', atualizarContador);
    atualizarContador();

    botaoLocal?.addEventListener('click', () => {
        if (!navigator.geolocation) {
            if (statusLocal) statusLocal.textContent = 'Geolocalização não disponível neste navegador.';
            return;
        }

        if (statusLocal) statusLocal.textContent = 'Obtendo localização...';

        navigator.geolocation.getCurrentPosition(
            ({ coords }) => {
                campoLatitude.value = String(coords.latitude);
                campoLongitude.value = String(coords.longitude);
                if (statusLocal) statusLocal.textContent = 'Localização obtida com sucesso.';
            },
            () => {
                if (statusLocal) statusLocal.textContent = 'Não foi possível obter a localização.';
            },
            { enableHighAccuracy: true, timeout: 10000 }
        );
    });
    function obterDadosFormulario() {
        const perigoSelecionado = form.querySelector('input[name="periculosidade"]:checked');
        const tipoSelecionado = campoTipo?.value;
        const tipoResiduo = TIPOS_RESIDUO[tipoSelecionado];

        if (!tipoResiduo) {
            throw new Error('Selecione um tipo de esgoto com mapeamento definido para a API.');
        }
        if (!perigoSelecionado) {
            throw new Error('Selecione o grau de perigo da denúncia.');
        }
        if (!ENDERECO_ID_CONFIGURADO) {
            throw new Error('A API exige um enderecoId cadastrado. Configure ENDERECO_ID_CONFIGURADO em denuncia.js.');
        }

        return {
            texto: campoDescricao.value.trim(),
            tipoResiduo,
            grauPerigo: perigoSelecionado.value,
            enderecoId: ENDERECO_ID_CONFIGURADO
        };
    }
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        if (mensagemSucesso) mensagemSucesso.hidden = true;

        try {
            const denuncia = obterDadosFormulario();

            if (denuncia.texto.length < 20 || denuncia.texto.length > 500) {
                throw new Error('A descrição deve conter entre 20 e 500 caracteres, conforme o backend.');
            }

            const resposta = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(denuncia)
            });

            if (!resposta.ok) {
                const detalhe = await resposta.text();
                throw new Error(detalhe || `Falha ao cadastrar denúncia (HTTP ${resposta.status}).`);
            }

            const resultado = await resposta.json();
            if (mensagemSucesso) {
                mensagemSucesso.textContent = 'Denúncia enviada com sucesso!';
                mensagemSucesso.hidden = false;
            }
            form.reset();
            atualizarContador();
            console.info('Denúncia cadastrada:', resultado);
        } catch (erro) {
            console.error('Não foi possível enviar a denúncia:', erro);
            window.alert(erro.message);
        }
    });
})();
