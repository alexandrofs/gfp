/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { TipoImpostoRendaDetailComponent } from 'app/entities/tipo-imposto-renda/tipo-imposto-renda-detail.component';
import { TipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

describe('Component Tests', () => {
    describe('TipoImpostoRenda Management Detail Component', () => {
        let comp: TipoImpostoRendaDetailComponent;
        let fixture: ComponentFixture<TipoImpostoRendaDetailComponent>;
        const route = ({ data: of({ tipoImpostoRenda: new TipoImpostoRenda(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TipoImpostoRendaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoImpostoRendaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoImpostoRendaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoImpostoRenda).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
