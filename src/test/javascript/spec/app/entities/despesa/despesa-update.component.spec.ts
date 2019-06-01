/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { DespesaUpdateComponent } from 'app/entities/despesa/despesa-update.component';
import { DespesaService } from 'app/entities/despesa/despesa.service';
import { Despesa } from 'app/shared/model/despesa.model';

describe('Component Tests', () => {
    describe('Despesa Management Update Component', () => {
        let comp: DespesaUpdateComponent;
        let fixture: ComponentFixture<DespesaUpdateComponent>;
        let service: DespesaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [DespesaUpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(DespesaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DespesaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DespesaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Despesa(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.updateForm(entity);
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Despesa();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.updateForm(entity);
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
