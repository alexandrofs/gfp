/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LancamentoCartaoComponentsPage, LancamentoCartaoDeleteDialog, LancamentoCartaoUpdatePage } from './lancamento-cartao.page-object';

const expect = chai.expect;

describe('LancamentoCartao e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let lancamentoCartaoUpdatePage: LancamentoCartaoUpdatePage;
    let lancamentoCartaoComponentsPage: LancamentoCartaoComponentsPage;
    /*let lancamentoCartaoDeleteDialog: LancamentoCartaoDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load LancamentoCartaos', async () => {
        await navBarPage.goToEntity('lancamento-cartao');
        lancamentoCartaoComponentsPage = new LancamentoCartaoComponentsPage();
        await browser.wait(ec.visibilityOf(lancamentoCartaoComponentsPage.title), 5000);
        expect(await lancamentoCartaoComponentsPage.getTitle()).to.eq('gfpApp.lancamentoCartao.home.title');
    });

    it('should load create LancamentoCartao page', async () => {
        await lancamentoCartaoComponentsPage.clickOnCreateButton();
        lancamentoCartaoUpdatePage = new LancamentoCartaoUpdatePage();
        expect(await lancamentoCartaoUpdatePage.getPageTitle()).to.eq('gfpApp.lancamentoCartao.home.createOrEditLabel');
        await lancamentoCartaoUpdatePage.cancel();
    });

    /* it('should create and save LancamentoCartaos', async () => {
        const nbButtonsBeforeCreate = await lancamentoCartaoComponentsPage.countDeleteButtons();

        await lancamentoCartaoComponentsPage.clickOnCreateButton();
        await promise.all([
            lancamentoCartaoUpdatePage.setDataCompraInput('2000-12-31'),
            lancamentoCartaoUpdatePage.setMesFaturaInput('2000-12-31'),
            lancamentoCartaoUpdatePage.setDescricaoInput('descricao'),
            lancamentoCartaoUpdatePage.setValorInput('5'),
            lancamentoCartaoUpdatePage.setUsuarioInput('usuario'),
            lancamentoCartaoUpdatePage.setQuantidadeParcelasInput('5'),
            lancamentoCartaoUpdatePage.setParcelaInput('5'),
            lancamentoCartaoUpdatePage.contaPagamentoSelectLastOption(),
        ]);
        expect(await lancamentoCartaoUpdatePage.getDataCompraInput()).to.eq('2000-12-31');
        expect(await lancamentoCartaoUpdatePage.getMesFaturaInput()).to.eq('2000-12-31');
        expect(await lancamentoCartaoUpdatePage.getDescricaoInput()).to.eq('descricao');
        expect(await lancamentoCartaoUpdatePage.getValorInput()).to.eq('5');
        expect(await lancamentoCartaoUpdatePage.getUsuarioInput()).to.eq('usuario');
        expect(await lancamentoCartaoUpdatePage.getQuantidadeParcelasInput()).to.eq('5');
        expect(await lancamentoCartaoUpdatePage.getParcelaInput()).to.eq('5');
        await lancamentoCartaoUpdatePage.save();
        expect(await lancamentoCartaoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await lancamentoCartaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last LancamentoCartao', async () => {
        const nbButtonsBeforeDelete = await lancamentoCartaoComponentsPage.countDeleteButtons();
        await lancamentoCartaoComponentsPage.clickOnLastDeleteButton();

        lancamentoCartaoDeleteDialog = new LancamentoCartaoDeleteDialog();
        expect(await lancamentoCartaoDeleteDialog.getDialogTitle())
            .to.eq('gfpApp.lancamentoCartao.delete.question');
        await lancamentoCartaoDeleteDialog.clickOnConfirmButton();

        expect(await lancamentoCartaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
