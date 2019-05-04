/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { CarteiraDetailComponent } from 'app/entities/carteira/carteira-detail.component';
import { Carteira } from 'app/shared/model/carteira.model';

describe('Component Tests', () => {
    describe('Carteira Management Detail Component', () => {
        let comp: CarteiraDetailComponent;
        let fixture: ComponentFixture<CarteiraDetailComponent>;
        const route = ({ data: of({ carteira: new Carteira(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CarteiraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CarteiraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarteiraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.carteira).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
