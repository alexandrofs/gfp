/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { CategoriaDespesaUpdateComponent } from 'app/entities/categoria-despesa/categoria-despesa-update.component';
import { CategoriaDespesaService } from 'app/entities/categoria-despesa/categoria-despesa.service';
import { CategoriaDespesa } from 'app/shared/model/categoria-despesa.model';

describe('Component Tests', () => {
    describe('CategoriaDespesa Management Update Component', () => {
        let comp: CategoriaDespesaUpdateComponent;
        let fixture: ComponentFixture<CategoriaDespesaUpdateComponent>;
        let service: CategoriaDespesaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CategoriaDespesaUpdateComponent]
            })
                .overrideTemplate(CategoriaDespesaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoriaDespesaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaDespesaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CategoriaDespesa(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.categoriaDespesa = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CategoriaDespesa();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.categoriaDespesa = entity;
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
