/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { LancamentoCartaoDetailComponent } from 'app/entities/lancamento-cartao/lancamento-cartao-detail.component';
import { LancamentoCartao } from 'app/shared/model/lancamento-cartao.model';

describe('Component Tests', () => {
    describe('LancamentoCartao Management Detail Component', () => {
        let comp: LancamentoCartaoDetailComponent;
        let fixture: ComponentFixture<LancamentoCartaoDetailComponent>;
        const route = ({ data: of({ lancamentoCartao: new LancamentoCartao(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [LancamentoCartaoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LancamentoCartaoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LancamentoCartaoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lancamentoCartao).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
