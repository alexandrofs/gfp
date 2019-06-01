/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { LancamentoCartaoUpdateComponent } from 'app/entities/lancamento-cartao/lancamento-cartao-update.component';
import { LancamentoCartaoService } from 'app/entities/lancamento-cartao/lancamento-cartao.service';
import { LancamentoCartao } from 'app/shared/model/lancamento-cartao.model';

describe('Component Tests', () => {
    describe('LancamentoCartao Management Update Component', () => {
        let comp: LancamentoCartaoUpdateComponent;
        let fixture: ComponentFixture<LancamentoCartaoUpdateComponent>;
        let service: LancamentoCartaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [LancamentoCartaoUpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(LancamentoCartaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LancamentoCartaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LancamentoCartaoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LancamentoCartao(123);
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
                const entity = new LancamentoCartao();
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
