import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'carteira',
                loadChildren: './carteira/carteira.module#GfpCarteiraModule'
            },
            {
                path: 'tipo-investimento',
                loadChildren: './tipo-investimento/tipo-investimento.module#GfpTipoInvestimentoModule'
            },
            {
                path: 'tipo-imposto-renda',
                loadChildren: './tipo-imposto-renda/tipo-imposto-renda.module#GfpTipoImpostoRendaModule'
            },
            {
                path: 'tabela-imposto-renda',
                loadChildren: './tabela-imposto-renda/tabela-imposto-renda.module#GfpTabelaImpostoRendaModule'
            },
            {
                path: 'instituicao',
                loadChildren: './instituicao/instituicao.module#GfpInstituicaoModule'
            },
            {
                path: 'indice-serie-di',
                loadChildren: './indice-serie-di/indice-serie-di.module#GfpIndiceSerieDiModule'
            },
            {
                path: 'investimento',
                loadChildren: './investimento/investimento.module#GfpInvestimentoModule'
            },
            {
                path: 'historico-cotas',
                loadChildren: './historico-cotas/historico-cotas.module#GfpHistoricoCotasModule'
            },
            {
                path: 'categoria-despesa',
                loadChildren: './categoria-despesa/categoria-despesa.module#GfpCategoriaDespesaModule'
            },
            {
                path: 'conta-pagamento',
                loadChildren: './conta-pagamento/conta-pagamento.module#GfpContaPagamentoModule'
            },
            {
                path: 'lancamento',
                loadChildren: './lancamento/lancamento.module#GfpLancamentoModule'
            },
            {
                path: 'lancamento-cartao',
                loadChildren: './lancamento-cartao/lancamento-cartao.module#GfpLancamentoCartaoModule'
            },
            {
                path: 'despesa',
                loadChildren: './despesa/despesa.module#GfpDespesaModule'
            },
            {
                path: 'lancamento-cartao',
                loadChildren: './lancamento-cartao/lancamento-cartao.module#GfpLancamentoCartaoModule'
            },
            {
                path: 'lancamento-cartao',
                loadChildren: './lancamento-cartao/lancamento-cartao.module#GfpLancamentoCartaoModule'
            },
            {
                path: 'lancamento',
                loadChildren: './lancamento/lancamento.module#GfpLancamentoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GfpEntityModule {}
