/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { InvestimentoDetailComponent } from 'app/entities/investimento/investimento-detail.component';
import { Investimento } from 'app/shared/model/investimento.model';

describe('Component Tests', () => {
    describe('Investimento Management Detail Component', () => {
        let comp: InvestimentoDetailComponent;
        let fixture: ComponentFixture<InvestimentoDetailComponent>;
        const route = ({ data: of({ investimento: new Investimento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [InvestimentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvestimentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvestimentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.investimento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
