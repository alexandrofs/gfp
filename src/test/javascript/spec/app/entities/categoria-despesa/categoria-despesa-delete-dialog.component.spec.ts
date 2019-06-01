/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { CategoriaDespesaDeleteDialogComponent } from 'app/entities/categoria-despesa/categoria-despesa-delete-dialog.component';
import { CategoriaDespesaService } from 'app/entities/categoria-despesa/categoria-despesa.service';

describe('Component Tests', () => {
    describe('CategoriaDespesa Management Delete Component', () => {
        let comp: CategoriaDespesaDeleteDialogComponent;
        let fixture: ComponentFixture<CategoriaDespesaDeleteDialogComponent>;
        let service: CategoriaDespesaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CategoriaDespesaDeleteDialogComponent]
            })
                .overrideTemplate(CategoriaDespesaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoriaDespesaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaDespesaService);
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
