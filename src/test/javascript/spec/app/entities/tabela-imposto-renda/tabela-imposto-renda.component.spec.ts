/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { TabelaImpostoRendaComponent } from 'app/entities/tabela-imposto-renda/tabela-imposto-renda.component';
import { TabelaImpostoRendaService } from 'app/entities/tabela-imposto-renda/tabela-imposto-renda.service';
import { TabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';

describe('Component Tests', () => {
    describe('TabelaImpostoRenda Management Component', () => {
        let comp: TabelaImpostoRendaComponent;
        let fixture: ComponentFixture<TabelaImpostoRendaComponent>;
        let service: TabelaImpostoRendaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TabelaImpostoRendaComponent],
                providers: []
            })
                .overrideTemplate(TabelaImpostoRendaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TabelaImpostoRendaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TabelaImpostoRendaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TabelaImpostoRenda(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tabelaImpostoRendas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
