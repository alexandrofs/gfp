/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { ContaPagamentoUpdateComponent } from 'app/entities/conta-pagamento/conta-pagamento-update.component';
import { ContaPagamentoService } from 'app/entities/conta-pagamento/conta-pagamento.service';
import { ContaPagamento } from 'app/shared/model/conta-pagamento.model';

describe('Component Tests', () => {
    describe('ContaPagamento Management Update Component', () => {
        let comp: ContaPagamentoUpdateComponent;
        let fixture: ComponentFixture<ContaPagamentoUpdateComponent>;
        let service: ContaPagamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [ContaPagamentoUpdateComponent]
            })
                .overrideTemplate(ContaPagamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContaPagamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContaPagamentoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContaPagamento(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contaPagamento = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ContaPagamento();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.contaPagamento = entity;
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
