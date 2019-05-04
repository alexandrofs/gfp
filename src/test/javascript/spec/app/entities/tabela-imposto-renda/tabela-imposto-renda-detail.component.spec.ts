/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { TabelaImpostoRendaDetailComponent } from 'app/entities/tabela-imposto-renda/tabela-imposto-renda-detail.component';
import { TabelaImpostoRenda } from 'app/shared/model/tabela-imposto-renda.model';

describe('Component Tests', () => {
    describe('TabelaImpostoRenda Management Detail Component', () => {
        let comp: TabelaImpostoRendaDetailComponent;
        let fixture: ComponentFixture<TabelaImpostoRendaDetailComponent>;
        const route = ({ data: of({ tabelaImpostoRenda: new TabelaImpostoRenda(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TabelaImpostoRendaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TabelaImpostoRendaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TabelaImpostoRendaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tabelaImpostoRenda).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
