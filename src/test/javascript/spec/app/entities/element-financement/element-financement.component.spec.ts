/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { ElementFinancementComponent } from '../../../../../../main/webapp/app/entities/element-financement/element-financement.component';
import { ElementFinancementService } from '../../../../../../main/webapp/app/entities/element-financement/element-financement.service';
import { ElementFinancement } from '../../../../../../main/webapp/app/entities/element-financement/element-financement.model';

describe('Component Tests', () => {

    describe('ElementFinancement Management Component', () => {
        let comp: ElementFinancementComponent;
        let fixture: ComponentFixture<ElementFinancementComponent>;
        let service: ElementFinancementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ElementFinancementComponent],
                providers: [
                    ElementFinancementService
                ]
            })
            .overrideTemplate(ElementFinancementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ElementFinancementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElementFinancementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ElementFinancement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.elementFinancements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
