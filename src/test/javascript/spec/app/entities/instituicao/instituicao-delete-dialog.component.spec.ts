/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GfpTestModule } from '../../../test.module';
import { InstituicaoDeleteDialogComponent } from 'app/entities/instituicao/instituicao-delete-dialog.component';
import { InstituicaoService } from 'app/entities/instituicao/instituicao.service';

describe('Component Tests', () => {
    describe('Instituicao Management Delete Component', () => {
        let comp: InstituicaoDeleteDialogComponent;
        let fixture: ComponentFixture<InstituicaoDeleteDialogComponent>;
        let service: InstituicaoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [InstituicaoDeleteDialogComponent]
            })
                .overrideTemplate(InstituicaoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InstituicaoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InstituicaoService);
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
