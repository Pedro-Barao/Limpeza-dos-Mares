(() => {
    'use strict';

    const API_URL = '/api/denuncias';
    const tabela = document.querySelector('#lista');
    if (!tabela) return;

    const campoBusca = document.querySelector('#busca');
    const filtroPerigo = document.querySelector('#f-perigo');
    const filtroTipo = document.querySelector('#f-tipo');
    const mensagemVazia = document.querySelector('#vazio');
    const filtrosStatus = document.querySelectorAll('[data-filtro]');

    let denuncias = [];
    let filtroStatusAtual = 'todas';

    const formatarTexto = (valor) => valor
        ? String(valor).replaceAll('_', ' ').toLocaleLowerCase('pt-BR')
            .replace(/\b\p{L}/gu, letra => letra.toLocaleUpperCase('pt-BR'))
        : 'Não informado';

    function criarCelula(valor) {
        const celula = document.createElement('td');
        celula.textContent = valor;
        return celula;
    }
    function renderizarDenuncias(lista) {
        tabela.replaceChildren();

        lista.forEach((denuncia) => {
            const linha = document.createElement('tr');
            const endereco = denuncia.endereco || {};
            const local = [endereco.logradouro, endereco.bairro, endereco.cidade]
                .filter(Boolean).join(', ') || 'Endereço não informado';

            linha.append(
                criarCelula(denuncia.id ?? '—'),
                criarCelula(local),
                criarCelula(formatarTexto(denuncia.tipoResiduo)),
                criarCelula(formatarTexto(denuncia.grauPerigo)),
                criarCelula('Status não disponibilizado pela API'),
                criarCelula('Não definido')
            );
            tabela.appendChild(linha);
        });

        if (mensagemVazia) mensagemVazia.hidden = lista.length > 0;
    }
    function atualizarContadores(lista) {
        const total = lista.length;
        const elementoTotal = document.querySelector('#n-todas');
        if (elementoTotal) elementoTotal.textContent = total;

        ['n-pendente', 'n-analise', 'n-resolvido'].forEach((id) => {
            const elemento = document.getElementById(id);
            if (elemento) elemento.textContent = '—';
        });
    }
    function aplicarFiltros() {
        const termo = (campoBusca?.value || '').trim().toLocaleLowerCase('pt-BR');
        const perigo = (filtroPerigo?.value || '').toUpperCase();
        const tipo = (filtroTipo?.value || '').toUpperCase();

        const resultado = denuncias.filter((denuncia) => {
            const endereco = denuncia.endereco || {};
            const textoBusca = [
                denuncia.id,
                denuncia.texto,
                endereco.logradouro,
                endereco.bairro,
                endereco.cidade
            ].filter(Boolean).join(' ').toLocaleLowerCase('pt-BR');

            const correspondeBusca = !termo || textoBusca.includes(termo);
            const correspondePerigo = !perigo || denuncia.grauPerigo === perigo;
            const correspondeTipo = !tipo || denuncia.tipoResiduo === tipo;

            const correspondeStatus = filtroStatusAtual === 'todas';
            return correspondeBusca && correspondePerigo && correspondeTipo && correspondeStatus;
        });

        renderizarDenuncias(resultado);
        atualizarContadores(resultado);
    }

    async function carregarDenuncias() {
        try {
            const resposta = await fetch(API_URL, {
                method: 'GET',
                headers: { 'Accept': 'application/json' }
            });

            if (!resposta.ok) {
                throw new Error(`Falha ao consultar denúncias (HTTP ${resposta.status}).`);
            }

            denuncias = await resposta.json();
            aplicarFiltros();
        } catch (erro) {
            console.error('Erro ao carregar denúncias:', erro);
            renderizarDenuncias([]);
            if (mensagemVazia) {
                mensagemVazia.textContent = 'Não foi possível carregar as denúncias da API.';
                mensagemVazia.hidden = false;
            }
        }
    }

    [campoBusca, filtroPerigo, filtroTipo].forEach((campo) => {
        campo?.addEventListener('input', aplicarFiltros);
        campo?.addEventListener('change', aplicarFiltros);
    });

    filtrosStatus.forEach((botao) => {
        botao.addEventListener('click', () => {
            filtroStatusAtual = botao.dataset.filtro || 'todas';
            filtrosStatus.forEach((item) => {
                item.setAttribute('aria-pressed', String(item === botao));
            });
            aplicarFiltros();
        });
    });

    carregarDenuncias();
})();
