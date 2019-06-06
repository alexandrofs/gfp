/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DespesaService } from 'app/entities/despesa/despesa.service';
import { IDespesa, Despesa } from 'app/shared/model/despesa.model';

describe('Service Tests', () => {
    describe('Despesa Service', () => {
        let injector: TestBed;
        let service: DespesaService;
        let httpMock: HttpTestingController;
        let elemDefault: IDespesa;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DespesaService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Despesa(0, currentDate, currentDate, 'AAAAAAA', 0, 0, 0, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataDespesa: currentDate.format(DATE_FORMAT),
                        mesReferencia: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Despesa', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataDespesa: currentDate.format(DATE_FORMAT),
                        mesReferencia: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataDespesa: currentDate,
                        mesReferencia: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Despesa(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Despesa', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataDespesa: currentDate.format(DATE_FORMAT),
                        mesReferencia: currentDate.format(DATE_FORMAT),
                        descricao: 'BBBBBB',
                        parcela: 1,
                        quantidadeParcelas: 1,
                        valor: 1,
                        usuario: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataDespesa: currentDate,
                        mesReferencia: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Despesa', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataDespesa: currentDate.format(DATE_FORMAT),
                        mesReferencia: currentDate.format(DATE_FORMAT),
                        descricao: 'BBBBBB',
                        parcela: 1,
                        quantidadeParcelas: 1,
                        valor: 1,
                        usuario: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataDespesa: currentDate,
                        mesReferencia: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Despesa', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
