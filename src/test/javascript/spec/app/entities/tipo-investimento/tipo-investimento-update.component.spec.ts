/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { TipoInvestimentoUpdateComponent } from 'app/entities/tipo-investimento/tipo-investimento-update.component';
import { TipoInvestimentoService } from 'app/entities/tipo-investimento/tipo-investimento.service';
import { TipoInvestimento } from 'app/shared/model/tipo-investimento.model';

describe('Component Tests', () => {
    describe('TipoInvestimento Management Update Component', () => {
        let comp: TipoInvestimentoUpdateComponent;
        let fixture: ComponentFixture<TipoInvestimentoUpdateComponent>;
        let service: TipoInvestimentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TipoInvestimentoUpdateComponent]
            })
                .overrideTemplate(TipoInvestimentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoInvestimentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoInvestimentoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoInvestimento(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoInvestimento = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TipoInvestimento();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tipoInvestimento = entity;
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
