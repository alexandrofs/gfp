/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LancamentoCartaoService } from 'app/entities/lancamento-cartao/lancamento-cartao.service';
import { ILancamentoCartao, LancamentoCartao } from 'app/shared/model/lancamento-cartao.model';

describe('Service Tests', () => {
    describe('LancamentoCartao Service', () => {
        let injector: TestBed;
        let service: LancamentoCartaoService;
        let httpMock: HttpTestingController;
        let elemDefault: ILancamentoCartao;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LancamentoCartaoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new LancamentoCartao(0, currentDate, currentDate, 'AAAAAAA', 0, 'AAAAAAA', 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataCompra: currentDate.format(DATE_FORMAT),
                        mesFatura: currentDate.format(DATE_FORMAT)
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

            it('should create a LancamentoCartao', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataCompra: currentDate.format(DATE_FORMAT),
                        mesFatura: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataCompra: currentDate,
                        mesFatura: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new LancamentoCartao(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a LancamentoCartao', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataCompra: currentDate.format(DATE_FORMAT),
                        mesFatura: currentDate.format(DATE_FORMAT),
                        descricao: 'BBBBBB',
                        valor: 1,
                        usuario: 'BBBBBB',
                        quantidadeParcelas: 1,
                        parcela: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataCompra: currentDate,
                        mesFatura: currentDate
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

            it('should return a list of LancamentoCartao', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataCompra: currentDate.format(DATE_FORMAT),
                        mesFatura: currentDate.format(DATE_FORMAT),
                        descricao: 'BBBBBB',
                        valor: 1,
                        usuario: 'BBBBBB',
                        quantidadeParcelas: 1,
                        parcela: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataCompra: currentDate,
                        mesFatura: currentDate
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

            it('should delete a LancamentoCartao', async () => {
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
