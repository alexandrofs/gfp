import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class DespesaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-despesa div table .btn-danger'));
    title = element.all(by.css('jhi-despesa div h2#page-heading span')).first();

    async clickOnCreateButton(timeout?: number) {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton(timeout?: number) {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DespesaUpdatePage {
    pageTitle = element(by.id('jhi-despesa-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dataDespesaInput = element(by.id('field_dataDespesa'));
    mesReferenciaInput = element(by.id('field_mesReferencia'));
    descricaoInput = element(by.id('field_descricao'));
    valorInput = element(by.id('field_valor'));
    usuarioInput = element(by.id('field_usuario'));
    contaPagamentoSelect = element(by.id('field_contaPagamento'));
    categoriaDespesaSelect = element(by.id('field_categoriaDespesa'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDataDespesaInput(dataDespesa) {
        await this.dataDespesaInput.sendKeys(dataDespesa);
    }

    async getDataDespesaInput() {
        return await this.dataDespesaInput.getAttribute('value');
    }

    async setMesReferenciaInput(mesReferencia) {
        await this.mesReferenciaInput.sendKeys(mesReferencia);
    }

    async getMesReferenciaInput() {
        return await this.mesReferenciaInput.getAttribute('value');
    }

    async setDescricaoInput(descricao) {
        await this.descricaoInput.sendKeys(descricao);
    }

    async getDescricaoInput() {
        return await this.descricaoInput.getAttribute('value');
    }

    async setValorInput(valor) {
        await this.valorInput.sendKeys(valor);
    }

    async getValorInput() {
        return await this.valorInput.getAttribute('value');
    }

    async setUsuarioInput(usuario) {
        await this.usuarioInput.sendKeys(usuario);
    }

    async getUsuarioInput() {
        return await this.usuarioInput.getAttribute('value');
    }

    async contaPagamentoSelectLastOption(timeout?: number) {
        await this.contaPagamentoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async contaPagamentoSelectOption(option) {
        await this.contaPagamentoSelect.sendKeys(option);
    }

    getContaPagamentoSelect(): ElementFinder {
        return this.contaPagamentoSelect;
    }

    async getContaPagamentoSelectedOption() {
        return await this.contaPagamentoSelect.element(by.css('option:checked')).getText();
    }

    async categoriaDespesaSelectLastOption(timeout?: number) {
        await this.categoriaDespesaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async categoriaDespesaSelectOption(option) {
        await this.categoriaDespesaSelect.sendKeys(option);
    }

    getCategoriaDespesaSelect(): ElementFinder {
        return this.categoriaDespesaSelect;
    }

    async getCategoriaDespesaSelectedOption() {
        return await this.categoriaDespesaSelect.element(by.css('option:checked')).getText();
    }

    async save(timeout?: number) {
        await this.saveButton.click();
    }

    async cancel(timeout?: number) {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class DespesaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-despesa-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-despesa'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton(timeout?: number) {
        await this.confirmButton.click();
    }
}
