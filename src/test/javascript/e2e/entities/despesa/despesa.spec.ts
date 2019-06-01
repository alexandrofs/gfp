/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DespesaComponentsPage, DespesaDeleteDialog, DespesaUpdatePage } from './despesa.page-object';

const expect = chai.expect;

describe('Despesa e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let despesaUpdatePage: DespesaUpdatePage;
    let despesaComponentsPage: DespesaComponentsPage;
    /*let despesaDeleteDialog: DespesaDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Despesas', async () => {
        await navBarPage.goToEntity('despesa');
        despesaComponentsPage = new DespesaComponentsPage();
        await browser.wait(ec.visibilityOf(despesaComponentsPage.title), 5000);
        expect(await despesaComponentsPage.getTitle()).to.eq('gfpApp.despesa.home.title');
    });

    it('should load create Despesa page', async () => {
        await despesaComponentsPage.clickOnCreateButton();
        despesaUpdatePage = new DespesaUpdatePage();
        expect(await despesaUpdatePage.getPageTitle()).to.eq('gfpApp.despesa.home.createOrEditLabel');
        await despesaUpdatePage.cancel();
    });

    /* it('should create and save Despesas', async () => {
        const nbButtonsBeforeCreate = await despesaComponentsPage.countDeleteButtons();

        await despesaComponentsPage.clickOnCreateButton();
        await promise.all([
            despesaUpdatePage.setDataDespesaInput('2000-12-31'),
            despesaUpdatePage.setMesReferenciaInput('2000-12-31'),
            despesaUpdatePage.setDescricaoInput('descricao'),
            despesaUpdatePage.setValorInput('5'),
            despesaUpdatePage.setUsuarioInput('usuario'),
            despesaUpdatePage.contaPagamentoSelectLastOption(),
            despesaUpdatePage.categoriaDespesaSelectLastOption(),
        ]);
        expect(await despesaUpdatePage.getDataDespesaInput()).to.eq('2000-12-31');
        expect(await despesaUpdatePage.getMesReferenciaInput()).to.eq('2000-12-31');
        expect(await despesaUpdatePage.getDescricaoInput()).to.eq('descricao');
        expect(await despesaUpdatePage.getValorInput()).to.eq('5');
        expect(await despesaUpdatePage.getUsuarioInput()).to.eq('usuario');
        await despesaUpdatePage.save();
        expect(await despesaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await despesaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last Despesa', async () => {
        const nbButtonsBeforeDelete = await despesaComponentsPage.countDeleteButtons();
        await despesaComponentsPage.clickOnLastDeleteButton();

        despesaDeleteDialog = new DespesaDeleteDialog();
        expect(await despesaDeleteDialog.getDialogTitle())
            .to.eq('gfpApp.despesa.delete.question');
        await despesaDeleteDialog.clickOnConfirmButton();

        expect(await despesaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
