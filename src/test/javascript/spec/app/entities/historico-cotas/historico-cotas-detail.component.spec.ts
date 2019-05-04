/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { HistoricoCotasDetailComponent } from 'app/entities/historico-cotas/historico-cotas-detail.component';
import { HistoricoCotas } from 'app/shared/model/historico-cotas.model';

describe('Component Tests', () => {
    describe('HistoricoCotas Management Detail Component', () => {
        let comp: HistoricoCotasDetailComponent;
        let fixture: ComponentFixture<HistoricoCotasDetailComponent>;
        const route = ({ data: of({ historicoCotas: new HistoricoCotas(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [HistoricoCotasDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HistoricoCotasDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HistoricoCotasDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.historicoCotas).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
