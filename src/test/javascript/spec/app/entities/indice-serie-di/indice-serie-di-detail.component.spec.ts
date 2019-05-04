/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { IndiceSerieDiDetailComponent } from 'app/entities/indice-serie-di/indice-serie-di-detail.component';
import { IndiceSerieDi } from 'app/shared/model/indice-serie-di.model';

describe('Component Tests', () => {
    describe('IndiceSerieDi Management Detail Component', () => {
        let comp: IndiceSerieDiDetailComponent;
        let fixture: ComponentFixture<IndiceSerieDiDetailComponent>;
        const route = ({ data: of({ indiceSerieDi: new IndiceSerieDi(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [IndiceSerieDiDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IndiceSerieDiDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IndiceSerieDiDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.indiceSerieDi).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
