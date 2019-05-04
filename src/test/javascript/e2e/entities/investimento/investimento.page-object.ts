import { element, by, ElementFinder } from 'protractor';

export class InvestimentoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-investimento div table .btn-danger'));
    title = element.all(by.css('jhi-investimento div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InvestimentoUpdatePage {
    pageTitle = element(by.id('jhi-investimento-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dataAplicacaoInput = element(by.id('field_dataAplicacao'));
    qtdeCotaInput = element(by.id('field_qtdeCota'));
    vlrCotaInput = element(by.id('field_vlrCota'));
    pctPrePosFixadoInput = element(by.id('field_pctPrePosFixado'));
    carteiraSelect = element(by.id('field_carteira'));
    tipoInvestimentoSelect = element(by.id('field_tipoInvestimento'));
    instituicaoSelect = element(by.id('field_instituicao'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDataAplicacaoInput(dataAplicacao) {
        await this.dataAplicacaoInput.sendKeys(dataAplicacao);
    }

    async getDataAplicacaoInput() {
        return this.dataAplicacaoInput.getAttribute('value');
    }

    async setQtdeCotaInput(qtdeCota) {
        await this.qtdeCotaInput.sendKeys(qtdeCota);
    }

    async getQtdeCotaInput() {
        return this.qtdeCotaInput.getAttribute('value');
    }

    async setVlrCotaInput(vlrCota) {
        await this.vlrCotaInput.sendKeys(vlrCota);
    }

    async getVlrCotaInput() {
        return this.vlrCotaInput.getAttribute('value');
    }

    async setPctPrePosFixadoInput(pctPrePosFixado) {
        await this.pctPrePosFixadoInput.sendKeys(pctPrePosFixado);
    }

    async getPctPrePosFixadoInput() {
        return this.pctPrePosFixadoInput.getAttribute('value');
    }

    async carteiraSelectLastOption() {
        await this.carteiraSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async carteiraSelectOption(option) {
        await this.carteiraSelect.sendKeys(option);
    }

    getCarteiraSelect(): ElementFinder {
        return this.carteiraSelect;
    }

    async getCarteiraSelectedOption() {
        return this.carteiraSelect.element(by.css('option:checked')).getText();
    }

    async tipoInvestimentoSelectLastOption() {
        await this.tipoInvestimentoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tipoInvestimentoSelectOption(option) {
        await this.tipoInvestimentoSelect.sendKeys(option);
    }

    getTipoInvestimentoSelect(): ElementFinder {
        return this.tipoInvestimentoSelect;
    }

    async getTipoInvestimentoSelectedOption() {
        return this.tipoInvestimentoSelect.element(by.css('option:checked')).getText();
    }

    async instituicaoSelectLastOption() {
        await this.instituicaoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async instituicaoSelectOption(option) {
        await this.instituicaoSelect.sendKeys(option);
    }

    getInstituicaoSelect(): ElementFinder {
        return this.instituicaoSelect;
    }

    async getInstituicaoSelectedOption() {
        return this.instituicaoSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class InvestimentoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-investimento-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-investimento'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
