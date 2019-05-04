/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { TipoImpostoRendaUpdateComponent } from 'app/entities/tipo-imposto-renda/tipo-imposto-renda-update.component';
import { TipoImpostoRendaService } from 'app/entities/tipo-imposto-renda/tipo-imposto-renda.service';
import { TipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

describe('Component Tests', () => {
    describe('TipoImpostoRenda Management Update Component', () => {
        let comp: TipoImpostoRendaUpdateComponent;
        let fixture: ComponentFixture<TipoImpostoRendaUpdateComponent>;
        let service: TipoImpostoRendaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TipoImpostoRendaUpdateComponent]
            })
                .overrideTemplate(TipoImpostoRendaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoImpostoRendaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoImpostoRendaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoImpostoRenda(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoImpostoRenda = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoImpostoRenda();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoImpostoRenda = entity;
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
