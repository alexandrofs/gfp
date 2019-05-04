/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { HistoricoCotasUpdateComponent } from 'app/entities/historico-cotas/historico-cotas-update.component';
import { HistoricoCotasService } from 'app/entities/historico-cotas/historico-cotas.service';
import { HistoricoCotas } from 'app/shared/model/historico-cotas.model';

describe('Component Tests', () => {
    describe('HistoricoCotas Management Update Component', () => {
        let comp: HistoricoCotasUpdateComponent;
        let fixture: ComponentFixture<HistoricoCotasUpdateComponent>;
        let service: HistoricoCotasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [HistoricoCotasUpdateComponent]
            })
                .overrideTemplate(HistoricoCotasUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HistoricoCotasUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoricoCotasService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new HistoricoCotas(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.historicoCotas = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new HistoricoCotas();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.historicoCotas = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
