/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { InstituicaoDetailComponent } from 'app/entities/instituicao/instituicao-detail.component';
import { Instituicao } from 'app/shared/model/instituicao.model';

describe('Component Tests', () => {
    describe('Instituicao Management Detail Component', () => {
        let comp: InstituicaoDetailComponent;
        let fixture: ComponentFixture<InstituicaoDetailComponent>;
        const route = ({ data: of({ instituicao: new Instituicao(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [InstituicaoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InstituicaoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InstituicaoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.instituicao).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
