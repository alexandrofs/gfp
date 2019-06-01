/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { ContaPagamentoDetailComponent } from 'app/entities/conta-pagamento/conta-pagamento-detail.component';
import { ContaPagamento } from 'app/shared/model/conta-pagamento.model';

describe('Component Tests', () => {
    describe('ContaPagamento Management Detail Component', () => {
        let comp: ContaPagamentoDetailComponent;
        let fixture: ComponentFixture<ContaPagamentoDetailComponent>;
        const route = ({ data: of({ contaPagamento: new ContaPagamento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [ContaPagamentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContaPagamentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContaPagamentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contaPagamento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
