/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { InstituicaoComponent } from 'app/entities/instituicao/instituicao.component';
import { InstituicaoService } from 'app/entities/instituicao/instituicao.service';
import { Instituicao } from 'app/shared/model/instituicao.model';

describe('Component Tests', () => {
    describe('Instituicao Management Component', () => {
        let comp: InstituicaoComponent;
        let fixture: ComponentFixture<InstituicaoComponent>;
        let service: InstituicaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [InstituicaoComponent],
                providers: []
            })
                .overrideTemplate(InstituicaoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InstituicaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InstituicaoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Instituicao(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.instituicaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
