import { element, by, ElementFinder } from 'protractor';

export class LancamentoCartaoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-lancamento-cartao div table .btn-danger'));
    title = element.all(by.css('jhi-lancamento-cartao div h2#page-heading span')).first();

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

export class LancamentoCartaoUpdatePage {
    pageTitle = element(by.id('jhi-lancamento-cartao-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dataCompraInput = element(by.id('field_dataCompra'));
    mesFaturaInput = element(by.id('field_mesFatura'));
    descricaoInput = element(by.id('field_descricao'));
    valorInput = element(by.id('field_valor'));
    usuarioInput = element(by.id('field_usuario'));
    contaPagamentoSelect = element(by.id('field_contaPagamento'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDataCompraInput(dataCompra) {
        await this.dataCompraInput.sendKeys(dataCompra);
    }

    async getDataCompraInput() {
        return this.dataCompraInput.getAttribute('value');
    }

    async setMesFaturaInput(mesFatura) {
        await this.mesFaturaInput.sendKeys(mesFatura);
    }

    async getMesFaturaInput() {
        return this.mesFaturaInput.getAttribute('value');
    }

    async setDescricaoInput(descricao) {
        await this.descricaoInput.sendKeys(descricao);
    }

    async getDescricaoInput() {
        return this.descricaoInput.getAttribute('value');
    }

    async setValorInput(valor) {
        await this.valorInput.sendKeys(valor);
    }

    async getValorInput() {
        return this.valorInput.getAttribute('value');
    }

    async setUsuarioInput(usuario) {
        await this.usuarioInput.sendKeys(usuario);
    }

    async getUsuarioInput() {
        return this.usuarioInput.getAttribute('value');
    }

    async contaPagamentoSelectLastOption() {
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
        return this.contaPagamentoSelect.element(by.css('option:checked')).getText();
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

export class LancamentoCartaoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-lancamentoCartao-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-lancamentoCartao'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
