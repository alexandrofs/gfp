/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { LancamentoCartaoDeleteDialogComponent } from 'app/entities/lancamento-cartao/lancamento-cartao-delete-dialog.component';
import { LancamentoCartaoService } from 'app/entities/lancamento-cartao/lancamento-cartao.service';

describe('Component Tests', () => {
    describe('LancamentoCartao Management Delete Component', () => {
        let comp: LancamentoCartaoDeleteDialogComponent;
        let fixture: ComponentFixture<LancamentoCartaoDeleteDialogComponent>;
        let service: LancamentoCartaoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [LancamentoCartaoDeleteDialogComponent]
            })
                .overrideTemplate(LancamentoCartaoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LancamentoCartaoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LancamentoCartaoService);
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
