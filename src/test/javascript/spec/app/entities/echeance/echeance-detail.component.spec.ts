/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { EcheanceDetailComponent } from '../../../../../../main/webapp/app/entities/echeance/echeance-detail.component';
import { EcheanceService } from '../../../../../../main/webapp/app/entities/echeance/echeance.service';
import { Echeance } from '../../../../../../main/webapp/app/entities/echeance/echeance.model';

describe('Component Tests', () => {

    describe('Echeance Management Detail Component', () => {
        let comp: EcheanceDetailComponent;
        let fixture: ComponentFixture<EcheanceDetailComponent>;
        let service: EcheanceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [EcheanceDetailComponent],
                providers: [
                    EcheanceService
                ]
            })
            .overrideTemplate(EcheanceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EcheanceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EcheanceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Echeance(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.echeance).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
