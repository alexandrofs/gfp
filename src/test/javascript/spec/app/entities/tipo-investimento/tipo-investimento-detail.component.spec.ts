/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { TipoInvestimentoDetailComponent } from 'app/entities/tipo-investimento/tipo-investimento-detail.component';
import { TipoInvestimento } from 'app/shared/model/tipo-investimento.model';

describe('Component Tests', () => {
    describe('TipoInvestimento Management Detail Component', () => {
        let comp: TipoInvestimentoDetailComponent;
        let fixture: ComponentFixture<TipoInvestimentoDetailComponent>;
        const route = ({ data: of({ tipoInvestimento: new TipoInvestimento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TipoInvestimentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoInvestimentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoInvestimentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoInvestimento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
