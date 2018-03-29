### Histórico da Revisão
| Data | Versão | Descrição | Autor |
|---|---|---|---|
| 26/03/2018 | 0.1 | Criação do documento | Larissa Sales e Letícia Meneses |
| 27/03/2018 | 0.2 | Definição das nomeações | Alexandre Miguel e Helena Goulart |
| 28/03/2018 | 0.3 | Adição de novos tópicos e correção dos topicos | Gabriel Albino e Helena Goulart |
| 28/03/2018 | 1.0 | Finalização dos tópicos que estavam pendentes no documento | Gabriel Albino e Letícia Meneses |



### Índice Analítico

  * [1 Nomeação](#1-nomeação)
    * [1.1 Pacotes](#11-pacotes)
    * [1.2 Classes](#12-classes)
    * [1.3 Objetos](#13-objetos)
    * [1.4 Interfaces](#14-interfaces)
    * [1.5 Funções](#15-funções)
    * [1.6 Atributos](#16-atributos)
    * [1.7 Variáveis](#17-variáveis)
  * [2 Formatação e Estilo](#2-formatação-e-estilo)
  * [3 Comentários](#3-comentários)
  * [4 Testes](#4-testes)
  * [5 Import](#5-import)
  * [6 Estruturas de Decisão](#6-estruturas-de-decisão)
  * [7 Identação](#7-identação)
  * [8 Linguagem](#8-linguagem)
  * [9 Estilo da Model](#9-estilo-da-model)
  * [10 Referências](#10-referências)



## 1 Nomeação
### 1.1 Pacotes
Seguem o padrão _UpperCamelCase_, ou seja, com todas as palavras iniciadas por letras maiúsculas e sem elemento separador, com termos preferencialmente suscintos e sem o uso de _underscores_.

Ex.:

  * Certo:

  ```kotlin
    PackageName
  ```
  * Errado:

  ```kotlin
    package_name
  ```

### 1.2 Classes
São nomeadas conforme o padrão _UpperCamelCase_, sem _underscores_.

Ex.:

  * Certo:

  ```kotlin
  ClassName
  ```
  * Errado:

  ```kotlin
  classname
  ```

### 1.3 Objetos
São nomeados conforme o padrão _lowerCamelCase_, ou seja, com a primeira palavra iniciada por letra minúscula e as demais palavras iniciadas por letras maiúsculas.

Ex.:

  * Certo:

  ```kotlin
  ObjectName
  ```

  * Errado:

  ```kotlin
  objectname
  ```

### 1.4 Interfaces
São nomeados conforme o padrão _UpperCamelCase_ e sem _underscores_.

Ex.:

  * Certo:

  ```kotlin
  InterfaceName
  ```
  * Errado:

  ```kotlin
  interfacename
  ```

### 1.5 Funções
A nomenclatura segue o padrão _lowerCamelCase_, sem o uso de _underscores_ e acompanhado de parênteses.

Ex.:

  * Certo:

  ```kotlin
  functionName()
  ```

  * Errado:

  ```kotlin
  Function_name()
  ```


### 1.6 Atributos
Segue o padrão _lowerCamelCase_, contendo nomes significativos e sem _underscores_.

Ex.:

  * Certo:

  ```kotlin
  attribute
  ```

  * Errado:

  ```
   Attribute_Example
  ```


### 1.7 Variáveis
Devem ser precedidas pela declaração do tipo _var_ para valores alteráveis ou _val_ para valores fixos, seguindo o padrão _lowerCamelCase_ , contendo nomes significativos e sem _underscores_.

Ex.:

  * Certo:
  ```kotlin
  var variableExample
  ```

  * Errado:

  ```kotlin
   Variable_Example
  ```


## 2 Formatação e Estilo

As chaves devem ser abertas no final de cada linha e fechadas em uma linha separada e alinhada com a mesma coluna em que foi aberta e haverá espaços após a escrita de classes, métodos e estruturas de decisão.

Ex.:

  * Certo:

  ```kotlin
  class Formating_Example {

         }
  ```

  * Errado:

  ```kotlin
  Formating_Example {

                                            }  
  ```


Pontos e vírgulas não serão utilizados no final de cada linha de código.

Ex.:

  * Certo:

  ```kotlin
  var rightExample
  ```
  * Errado:

  ```kotlin
  Errado: var wrongExample;
  ```

Serão usados espaços entre operações lógicas, como somas e subtrações, todavia não serão utilizados em casos de incrementação.

Ex.:

  * Certo:

  ```kotlin
  rightExample = 1 + 1
  ```

  * Errado:

  ```kotlin
  wrongExample=1+1
  ```
  * Certo:

  ```kotlin
  i++
  ```
  * Errado:

  ```kotlin
  i + +
  ```


## 3 Comentários

Devem iniciar com letra maiúscula e ter espaço apoś "\\". Para uma maior compreensão do código, os comentários devem estar presentes neles como uma breve apresentação do objetivo e como o método funciona.

Ex.:

  - Certo:

  ```kotlin
  \\ Right example
    ```
  * Errado:

  ```
  \\wrong example
  ```
Ex.:

  Métodos

  * Certo:

  ```kotlin
// Adds a [member] to this group.
@return the new size of the group.
//
fun add(member: T): Int { ... }
```

  * Errado:

  ```kotlin
   fun add(member: T): Int { ... }
  ```

Devem ser escritos no mesmo idioma do código;

Ex.:

  * Certo:

  ```kotlin
  Right example
  ```
  * Errado:

  ```
  Exemplo errado
  ```

Comentários de uma linha devem ser escritos usando `//`

Ex.:

  * Certo:

  ```kotlin
  Right example
  ```

  * Errado:

  ```
   /* Wrong example */
  ```

Comentários de mais de uma linha devem ser escritos usando `/* */`

Ex.:

  * Certo:

  ```kotlin
   /* This is an example about
         how to use correctly the comentar
         on the code */
  ```

  * Errado:

  ```
   // This is not the right example
          // about using a comentar
  ```


## 4 Testes

Utilizar comentários de uma linha para descrever cada etapa do teste

Ex.:

  * Certo:

  ```kotlin
  @Test
  fun testSuccess_basic() {
    // prepare
    val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
    val response = Response.success(redditNewsResponse)

    when (callMock.execute()).thenReturn(response)

    // call
    val newsManager = NewsManager(apiMock)
    newsManager.getNews("").subscribe(testSub)

    // assert
    testSub.assertNoErrors()
    testSub.assertValueCount(1)
    testSub.assertCompleted()
  }
  ```
  * Errado:

  ```kotlin
  @Test
  fun testSuccess_basic() {
    val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
    val response = Response.success(redditNewsResponse)
    when (callMock.execute()).thenReturn(response)
    val newsManager = NewsManager(apiMock)
    newsManager.getNews("").subscribe(testSub)
    testSub.assertNoErrors()
    testSub.assertValueCount(1)
    testSub.assertCompleted()
  }
  ```

  Utilizarão do padrão _UpperCamelCase_ e permitirão o uso de _underscores_.

  Ex.:

  * Certo:
   ```kotlin
  class Test_Case {

         }
  ```

  * Errado:

  ```kotlin
   class testcase {

         }

  ```

## 5 Import

As importações deverão usar o termo _import_ seguido do nome do pacote ou classe, e em caso de mais de uma chamada é necessário que seja realizada em uma linha separada.

  Ex.:

  * Certo:
   ```kotlin
  import NameOfPackage
  import NameOfClass
  ```

  * Errado:
  ```kotlin
   import NameOfPackage, import NameOfCLass
  ```


## 6 Estruturas de Decisão

Serão colocados espaços entre essas estruturas e os parênteses que contém os termos. Sempre serão usadas chaves para implementar o corpo das condicionais.

Ex.:

  * Certo:

  ```kotlin
  if (rightExample == right) {
          return rightExample
        }
  ```
  * Errado:

  ```
   if(wrongExample == wrong)
            return wrongExample
  ```

## 7 Identação

A identação deve ser de 4 espaços para cada nível;

Ex.:

  * Certo:

  ```kotlin
  if (examples != null) {
             examples == 1
         }
  ```
  * Errado:

  ```kotlin
   if (examples == null) {
            examples == null
          }
  ```

## 8 Linguagem

O linguagem padrão do programa é em inglês, extendendo-se para pacotes, classes, objetos, atributos e variáveis.

Ex.:

  * Certo:

  ```kotlin
  class GreatExample;
  ```
  * Errado:

  ```kotlin
  Errado: classe ExemploErrado;
  ```

## 9 Estilo da Model

A _Model_ deve ser organizada de modo em que possua 3 classes internas, que serão a _request_, a _response_ e a _ViewModel_

Ex.:

  * Certo:

  ````kotlin
    class Model {
        class Request {
            var name: String? = null
            constructor(name: String?) {
                this.name = name
            }
        }
        class Response {
            var age: Int? = null
            constructor(age: Int?) {
                this.age = age
            }
        }
        class ViewModel {
            var message: String?= null
            constructor(message: String?) {
                this.message = message
            }
        }
    }
```

  * Errado:

  ```
    class Model {
        var age: Int? = null
        var name: String? = null
    }
  ```

## 10 Referências
[Kotlin Style Guide](https://kotlinlang.org/docs/reference/coding-conventions.html)