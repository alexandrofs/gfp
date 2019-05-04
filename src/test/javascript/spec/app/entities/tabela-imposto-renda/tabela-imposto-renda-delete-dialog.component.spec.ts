/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { TabelaImpostoRendaDeleteDialogComponent } from 'app/entities/tabela-imposto-renda/tabela-imposto-renda-delete-dialog.component';
import { TabelaImpostoRendaService } from 'app/entities/tabela-imposto-renda/tabela-imposto-renda.service';

describe('Component Tests', () => {
    describe('TabelaImpostoRenda Management Delete Component', () => {
        let comp: TabelaImpostoRendaDeleteDialogComponent;
        let fixture: ComponentFixture<TabelaImpostoRendaDeleteDialogComponent>;
        let service: TabelaImpostoRendaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TabelaImpostoRendaDeleteDialogComponent]
            })
                .overrideTemplate(TabelaImpostoRendaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TabelaImpostoRendaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TabelaImpostoRendaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
