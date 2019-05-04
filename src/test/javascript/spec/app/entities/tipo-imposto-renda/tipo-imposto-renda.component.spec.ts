/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { TipoImpostoRendaComponent } from 'app/entities/tipo-imposto-renda/tipo-imposto-renda.component';
import { TipoImpostoRendaService } from 'app/entities/tipo-imposto-renda/tipo-imposto-renda.service';
import { TipoImpostoRenda } from 'app/shared/model/tipo-imposto-renda.model';

describe('Component Tests', () => {
    describe('TipoImpostoRenda Management Component', () => {
        let comp: TipoImpostoRendaComponent;
        let fixture: ComponentFixture<TipoImpostoRendaComponent>;
        let service: TipoImpostoRendaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [TipoImpostoRendaComponent],
                providers: []
            })
                .overrideTemplate(TipoImpostoRendaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoImpostoRendaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoImpostoRendaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoImpostoRenda(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tipoImpostoRendas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
