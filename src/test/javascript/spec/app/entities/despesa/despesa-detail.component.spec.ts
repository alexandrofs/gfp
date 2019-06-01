/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { DespesaDetailComponent } from 'app/entities/despesa/despesa-detail.component';
import { Despesa } from 'app/shared/model/despesa.model';

describe('Component Tests', () => {
    describe('Despesa Management Detail Component', () => {
        let comp: DespesaDetailComponent;
        let fixture: ComponentFixture<DespesaDetailComponent>;
        const route = ({ data: of({ despesa: new Despesa(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [DespesaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DespesaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DespesaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.despesa).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
