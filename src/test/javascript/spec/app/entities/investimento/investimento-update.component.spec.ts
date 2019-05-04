/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { InvestimentoUpdateComponent } from 'app/entities/investimento/investimento-update.component';
import { InvestimentoService } from 'app/entities/investimento/investimento.service';
import { Investimento } from 'app/shared/model/investimento.model';

describe('Component Tests', () => {
    describe('Investimento Management Update Component', () => {
        let comp: InvestimentoUpdateComponent;
        let fixture: ComponentFixture<InvestimentoUpdateComponent>;
        let service: InvestimentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [InvestimentoUpdateComponent]
            })
                .overrideTemplate(InvestimentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvestimentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvestimentoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Investimento(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.investimento = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Investimento();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.investimento = entity;
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
