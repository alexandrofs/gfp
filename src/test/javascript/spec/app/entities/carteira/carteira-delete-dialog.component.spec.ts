/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { CarteiraDeleteDialogComponent } from 'app/entities/carteira/carteira-delete-dialog.component';
import { CarteiraService } from 'app/entities/carteira/carteira.service';

describe('Component Tests', () => {
    describe('Carteira Management Delete Component', () => {
        let comp: CarteiraDeleteDialogComponent;
        let fixture: ComponentFixture<CarteiraDeleteDialogComponent>;
        let service: CarteiraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CarteiraDeleteDialogComponent]
            })
                .overrideTemplate(CarteiraDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarteiraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteiraService);
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
