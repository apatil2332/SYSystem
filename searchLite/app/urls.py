from django.urls import path
from . import views

urlpatterns = [
    path('homepage/', views.homepage, name='homepage'),
    path('', views.homepage, name='homepage'),
    path('upload/', views.upload, name='upload'),
    path('search/', views.search, name='search'),
    path('results/', views.results, name='results'),
    path('view/<int:doc_id>', views.view_document, name='view_document'),
    path('load_image_document/<int:doc_id>/', views.load_image_document, name='load_image_document'),
    path('load_document/<int:doc_id>/', views.view_pdf_document, name='load_document'),
]
