/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { TabelaImpostoRendaUpdateComponent } from 'app/entities/tabela-imposto-renda/tabela-imposto-renda-update.component';
import { TabelaImpostoRendaService } from 'app/entities/tabela-imposto-renda/tabela-imposto-renda.service';
import { TabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';

describe('Component Tests', () => {
    describe('TabelaImpostoRenda Management Update Component', () => {
        let comp: TabelaImpostoRendaUpdateComponent;
        let fixture: ComponentFixture<TabelaImpostoRendaUpdateComponent>;
        let service: TabelaImpostoRendaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TabelaImpostoRendaUpdateComponent]
            })
                .overrideTemplate(TabelaImpostoRendaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TabelaImpostoRendaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TabelaImpostoRendaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TabelaImpostoRenda(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tabelaImpostoRenda = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TabelaImpostoRenda();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tabelaImpostoRenda = entity;
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
