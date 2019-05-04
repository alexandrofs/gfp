import { element, by, ElementFinder } from 'protractor';

export class TipoImpostoRendaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-tipo-imposto-renda div table .btn-danger'));
    title = element.all(by.css('jhi-tipo-imposto-renda div h2#page-heading span')).first();

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

export class TipoImpostoRendaUpdatePage {
    pageTitle = element(by.id('jhi-tipo-imposto-renda-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codigoInput = element(by.id('field_codigo'));
    descricaoInput = element(by.id('field_descricao'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCodigoInput(codigo) {
        await this.codigoInput.sendKeys(codigo);
    }

    async getCodigoInput() {
        return this.codigoInput.getAttribute('value');
    }

    async setDescricaoInput(descricao) {
        await this.descricaoInput.sendKeys(descricao);
    }

    async getDescricaoInput() {
        return this.descricaoInput.getAttribute('value');
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

export class TipoImpostoRendaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-tipoImpostoRenda-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-tipoImpostoRenda'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
