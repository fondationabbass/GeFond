/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { GarantieDetailComponent } from '../../../../../../main/webapp/app/entities/garantie/garantie-detail.component';
import { GarantieService } from '../../../../../../main/webapp/app/entities/garantie/garantie.service';
import { Garantie } from '../../../../../../main/webapp/app/entities/garantie/garantie.model';

describe('Component Tests', () => {

    describe('Garantie Management Detail Component', () => {
        let comp: GarantieDetailComponent;
        let fixture: ComponentFixture<GarantieDetailComponent>;
        let service: GarantieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [GarantieDetailComponent],
                providers: [
                    GarantieService
                ]
            })
            .overrideTemplate(GarantieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GarantieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GarantieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Garantie(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.garantie).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
