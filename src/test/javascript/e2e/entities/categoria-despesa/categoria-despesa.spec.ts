/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CategoriaDespesaComponentsPage, CategoriaDespesaDeleteDialog, CategoriaDespesaUpdatePage } from './categoria-despesa.page-object';

const expect = chai.expect;

describe('CategoriaDespesa e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let categoriaDespesaUpdatePage: CategoriaDespesaUpdatePage;
    let categoriaDespesaComponentsPage: CategoriaDespesaComponentsPage;
    let categoriaDespesaDeleteDialog: CategoriaDespesaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CategoriaDespesas', async () => {
        await navBarPage.goToEntity('categoria-despesa');
        categoriaDespesaComponentsPage = new CategoriaDespesaComponentsPage();
        await browser.wait(ec.visibilityOf(categoriaDespesaComponentsPage.title), 5000);
        expect(await categoriaDespesaComponentsPage.getTitle()).to.eq('gfpApp.categoriaDespesa.home.title');
    });

    it('should load create CategoriaDespesa page', async () => {
        await categoriaDespesaComponentsPage.clickOnCreateButton();
        categoriaDespesaUpdatePage = new CategoriaDespesaUpdatePage();
        expect(await categoriaDespesaUpdatePage.getPageTitle()).to.eq('gfpApp.categoriaDespesa.home.createOrEditLabel');
        await categoriaDespesaUpdatePage.cancel();
    });

    it('should create and save CategoriaDespesas', async () => {
        const nbButtonsBeforeCreate = await categoriaDespesaComponentsPage.countDeleteButtons();

        await categoriaDespesaComponentsPage.clickOnCreateButton();
        await promise.all([categoriaDespesaUpdatePage.setNomeInput('nome'), categoriaDespesaUpdatePage.setUsuarioInput('usuario')]);
        expect(await categoriaDespesaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await categoriaDespesaUpdatePage.getUsuarioInput()).to.eq('usuario', 'Expected Usuario value to be equals to usuario');
        await categoriaDespesaUpdatePage.save();
        expect(await categoriaDespesaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await categoriaDespesaComponentsPage.countDeleteButtons()).to.eq(
            nbButtonsBeforeCreate + 1,
            'Expected one more entry in the table'
        );
    });

    it('should delete last CategoriaDespesa', async () => {
        const nbButtonsBeforeDelete = await categoriaDespesaComponentsPage.countDeleteButtons();
        await categoriaDespesaComponentsPage.clickOnLastDeleteButton();

        categoriaDespesaDeleteDialog = new CategoriaDespesaDeleteDialog();
        expect(await categoriaDespesaDeleteDialog.getDialogTitle()).to.eq('gfpApp.categoriaDespesa.delete.question');
        await categoriaDespesaDeleteDialog.clickOnConfirmButton();

        expect(await categoriaDespesaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
