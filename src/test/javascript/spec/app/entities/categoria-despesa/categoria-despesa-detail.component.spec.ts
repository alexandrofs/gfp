/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { CategoriaDespesaDetailComponent } from 'app/entities/categoria-despesa/categoria-despesa-detail.component';
import { CategoriaDespesa } from 'app/shared/model/categoria-despesa.model';

describe('Component Tests', () => {
    describe('CategoriaDespesa Management Detail Component', () => {
        let comp: CategoriaDespesaDetailComponent;
        let fixture: ComponentFixture<CategoriaDespesaDetailComponent>;
        const route = ({ data: of({ categoriaDespesa: new CategoriaDespesa(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CategoriaDespesaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CategoriaDespesaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoriaDespesaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.categoriaDespesa).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
