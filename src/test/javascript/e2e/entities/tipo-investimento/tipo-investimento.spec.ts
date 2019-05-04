/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoInvestimentoComponentsPage, TipoInvestimentoDeleteDialog, TipoInvestimentoUpdatePage } from './tipo-investimento.page-object';

const expect = chai.expect;

describe('TipoInvestimento e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoInvestimentoUpdatePage: TipoInvestimentoUpdatePage;
    let tipoInvestimentoComponentsPage: TipoInvestimentoComponentsPage;
    /*let tipoInvestimentoDeleteDialog: TipoInvestimentoDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoInvestimentos', async () => {
        await navBarPage.goToEntity('tipo-investimento');
        tipoInvestimentoComponentsPage = new TipoInvestimentoComponentsPage();
        await browser.wait(ec.visibilityOf(tipoInvestimentoComponentsPage.title), 5000);
        expect(await tipoInvestimentoComponentsPage.getTitle()).to.eq('gfpApp.tipoInvestimento.home.title');
    });

    it('should load create TipoInvestimento page', async () => {
        await tipoInvestimentoComponentsPage.clickOnCreateButton();
        tipoInvestimentoUpdatePage = new TipoInvestimentoUpdatePage();
        expect(await tipoInvestimentoUpdatePage.getPageTitle()).to.eq('gfpApp.tipoInvestimento.home.createOrEditLabel');
        await tipoInvestimentoUpdatePage.cancel();
    });

    /* it('should create and save TipoInvestimentos', async () => {
        const nbButtonsBeforeCreate = await tipoInvestimentoComponentsPage.countDeleteButtons();

        await tipoInvestimentoComponentsPage.clickOnCreateButton();
        await promise.all([
            tipoInvestimentoUpdatePage.setNomeInput('nome'),
            tipoInvestimentoUpdatePage.setDescricaoInput('descricao'),
            tipoInvestimentoUpdatePage.setModalidadeInput('modalidade'),
            tipoInvestimentoUpdatePage.setTipoIndexadorInput('tipoIndexador'),
            tipoInvestimentoUpdatePage.setIndiceInput('indice'),
            tipoInvestimentoUpdatePage.tipoImpostoRendaSelectLastOption(),
        ]);
        expect(await tipoInvestimentoUpdatePage.getNomeInput()).to.eq('nome');
        expect(await tipoInvestimentoUpdatePage.getDescricaoInput()).to.eq('descricao');
        expect(await tipoInvestimentoUpdatePage.getModalidadeInput()).to.eq('modalidade');
        expect(await tipoInvestimentoUpdatePage.getTipoIndexadorInput()).to.eq('tipoIndexador');
        expect(await tipoInvestimentoUpdatePage.getIndiceInput()).to.eq('indice');
        await tipoInvestimentoUpdatePage.save();
        expect(await tipoInvestimentoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoInvestimentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last TipoInvestimento', async () => {
        const nbButtonsBeforeDelete = await tipoInvestimentoComponentsPage.countDeleteButtons();
        await tipoInvestimentoComponentsPage.clickOnLastDeleteButton();

        tipoInvestimentoDeleteDialog = new TipoInvestimentoDeleteDialog();
        expect(await tipoInvestimentoDeleteDialog.getDialogTitle())
            .to.eq('gfpApp.tipoInvestimento.delete.question');
        await tipoInvestimentoDeleteDialog.clickOnConfirmButton();

        expect(await tipoInvestimentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
