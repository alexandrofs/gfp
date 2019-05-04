/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { HistoricoCotasDeleteDialogComponent } from 'app/entities/historico-cotas/historico-cotas-delete-dialog.component';
import { HistoricoCotasService } from 'app/entities/historico-cotas/historico-cotas.service';

describe('Component Tests', () => {
    describe('HistoricoCotas Management Delete Component', () => {
        let comp: HistoricoCotasDeleteDialogComponent;
        let fixture: ComponentFixture<HistoricoCotasDeleteDialogComponent>;
        let service: HistoricoCotasService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [HistoricoCotasDeleteDialogComponent]
            })
                .overrideTemplate(HistoricoCotasDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoricoCotasDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoricoCotasService);
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
