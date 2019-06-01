/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { ContaPagamentoDeleteDialogComponent } from 'app/entities/conta-pagamento/conta-pagamento-delete-dialog.component';
import { ContaPagamentoService } from 'app/entities/conta-pagamento/conta-pagamento.service';

describe('Component Tests', () => {
    describe('ContaPagamento Management Delete Component', () => {
        let comp: ContaPagamentoDeleteDialogComponent;
        let fixture: ComponentFixture<ContaPagamentoDeleteDialogComponent>;
        let service: ContaPagamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [ContaPagamentoDeleteDialogComponent]
            })
                .overrideTemplate(ContaPagamentoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContaPagamentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContaPagamentoService);
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
