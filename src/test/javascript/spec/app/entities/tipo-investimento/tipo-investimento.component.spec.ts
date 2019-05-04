/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { TipoInvestimentoComponent } from 'app/entities/tipo-investimento/tipo-investimento.component';
import { TipoInvestimentoService } from 'app/entities/tipo-investimento/tipo-investimento.service';
import { TipoInvestimento } from 'app/shared/model/tipo-investimento.model';

describe('Component Tests', () => {
    describe('TipoInvestimento Management Component', () => {
        let comp: TipoInvestimentoComponent;
        let fixture: ComponentFixture<TipoInvestimentoComponent>;
        let service: TipoInvestimentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TipoInvestimentoComponent],
                providers: []
            })
                .overrideTemplate(TipoInvestimentoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoInvestimentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoInvestimentoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoInvestimento(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tipoInvestimentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
