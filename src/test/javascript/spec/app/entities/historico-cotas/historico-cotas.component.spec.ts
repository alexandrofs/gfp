/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GfpTestModule } from '../../../test.module';
import { HistoricoCotasComponent } from 'app/entities/historico-cotas/historico-cotas.component';
import { HistoricoCotasService } from 'app/entities/historico-cotas/historico-cotas.service';
import { HistoricoCotas } from 'app/shared/model/historico-cotas.model';

describe('Component Tests', () => {
    describe('HistoricoCotas Management Component', () => {
        let comp: HistoricoCotasComponent;
        let fixture: ComponentFixture<HistoricoCotasComponent>;
        let service: HistoricoCotasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [HistoricoCotasComponent],
                providers: []
            })
                .overrideTemplate(HistoricoCotasComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HistoricoCotasComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoricoCotasService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new HistoricoCotas(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.historicoCotas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
