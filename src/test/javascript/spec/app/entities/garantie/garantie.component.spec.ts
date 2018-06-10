/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { GarantieComponent } from '../../../../../../main/webapp/app/entities/garantie/garantie.component';
import { GarantieService } from '../../../../../../main/webapp/app/entities/garantie/garantie.service';
import { Garantie } from '../../../../../../main/webapp/app/entities/garantie/garantie.model';

describe('Component Tests', () => {

    describe('Garantie Management Component', () => {
        let comp: GarantieComponent;
        let fixture: ComponentFixture<GarantieComponent>;
        let service: GarantieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [GarantieComponent],
                providers: [
                    GarantieService
                ]
            })
            .overrideTemplate(GarantieComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GarantieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GarantieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Garantie(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.garanties[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
