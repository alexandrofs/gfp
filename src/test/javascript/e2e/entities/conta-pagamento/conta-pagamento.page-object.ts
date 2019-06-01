import { element, by, ElementFinder } from 'protractor';

export class ContaPagamentoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-conta-pagamento div table .btn-danger'));
    title = element.all(by.css('jhi-conta-pagamento div h2#page-heading span')).first();

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

export class ContaPagamentoUpdatePage {
    pageTitle = element(by.id('jhi-conta-pagamento-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomeInput = element(by.id('field_nome'));
    tipoContaSelect = element(by.id('field_tipoConta'));
    usuarioInput = element(by.id('field_usuario'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomeInput(nome) {
        await this.nomeInput.sendKeys(nome);
    }

    async getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    async setTipoContaSelect(tipoConta) {
        await this.tipoContaSelect.sendKeys(tipoConta);
    }

    async getTipoContaSelect() {
        return this.tipoContaSelect.element(by.css('option:checked')).getText();
    }

    async tipoContaSelectLastOption() {
        await this.tipoContaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setUsuarioInput(usuario) {
        await this.usuarioInput.sendKeys(usuario);
    }

    async getUsuarioInput() {
        return this.usuarioInput.getAttribute('value');
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

export class ContaPagamentoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contaPagamento-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contaPagamento'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
