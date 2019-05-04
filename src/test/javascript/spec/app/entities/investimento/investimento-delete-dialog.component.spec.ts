/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { InvestimentoDeleteDialogComponent } from 'app/entities/investimento/investimento-delete-dialog.component';
import { InvestimentoService } from 'app/entities/investimento/investimento.service';

describe('Component Tests', () => {
    describe('Investimento Management Delete Component', () => {
        let comp: InvestimentoDeleteDialogComponent;
        let fixture: ComponentFixture<InvestimentoDeleteDialogComponent>;
        let service: InvestimentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [InvestimentoDeleteDialogComponent]
            })
                .overrideTemplate(InvestimentoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvestimentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvestimentoService);
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
