import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { map } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { Product } from '../_model/product.model';
import { ImageProcessingService } from '../_services/image-processing.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  pageNumber: number = 0
  productDetails: Product[] = [];

  showLoadButton = false;

  constructor(private productService : ProductService,
    private imageProcessingService: ImageProcessingService,
    private router: Router
  ){}
  ngOnInit(): void {
    this.getAllProducts()
  }

  public getAllProducts(searchKey : string = "") {
    this.productService.getAllProducts(this.pageNumber, searchKey)
    .pipe(
      map((x: Product[], i:any) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    )
    .subscribe({
      next: (resp: Product[]) => {
        console.log(resp);
        if(resp.length == 8){
          this.showLoadButton = true
        }else{
          this.showLoadButton = false
        }
        resp.forEach(p => this.productDetails.push(p))
      },
      error: (error: HttpErrorResponse) => {
        console.log(error);
      }
    }

    )
  }

  public showProductDetails(productId : any){
    this.router.navigate(['/productViewDetails', {productId: productId}])
  }

  public loadMoreProduct(){
    this.pageNumber += 1;
    this.getAllProducts()
  }
  searchByKeyword(searchkeywords : string){
    this.pageNumber = 0;
    this.productDetails = []  
    this.getAllProducts(searchkeywords)  
  }

}
